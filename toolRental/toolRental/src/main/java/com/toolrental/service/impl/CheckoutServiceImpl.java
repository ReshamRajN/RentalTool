package com.toolrental.service.impl;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import com.toolrental.validator.CheckoutValidator;
import com.toolrental.model.Charge;
import com.toolrental.model.RentalAgreement;
import com.toolrental.model.Tool;
import com.toolrental.repository.impl.ChargeRepositoryImpl;
import com.toolrental.repository.impl.ToolRepositoryImpl;
import com.toolrental.service.CheckoutService;
import com.toolrental.utils.DateUtils;

public class CheckoutServiceImpl implements CheckoutService {

	private ToolRepositoryImpl toolRepository = new ToolRepositoryImpl();
	private ChargeRepositoryImpl chargeRepository = new ChargeRepositoryImpl();

	public RentalAgreement checkout(String toolCode, String checkout, int rentalDayCount, String discount) {

		Date checkoutDate = DateUtils.parseDate(checkout);
		int discountPercent = Integer.parseInt(discount.split("%")[0]);
		DecimalFormat f = new DecimalFormat("$##.00");

		RentalAgreement agreement = null;
		try {
			if (CheckoutValidator.validate(toolCode, rentalDayCount, discountPercent, checkoutDate)) {
				Tool tool = null;
				Charge charge = null;
				tool = toolRepository.getToolByCode(toolCode);
				if (tool != null) {
					charge = chargeRepository.getChargeByToolType(tool.getType());
					if (charge != null) {
						agreement = new RentalAgreement();

						Date dueDate = DateUtils.addDays(checkoutDate, rentalDayCount);
						Integer chargableDays = getChargableDays(checkoutDate, dueDate, charge);
						Double preDiscountCharge = chargableDays * charge.getDailyCharge();
						Double discountAmount = (discountPercent * preDiscountCharge) / 100;
						Double finalCharge = preDiscountCharge - discountAmount;
						agreement.setToolCode(toolCode);
						agreement.setToolType(tool.getType());
						agreement.setBrand(tool.getBrand());
						agreement.setRentalDays(rentalDayCount);
						agreement.setCheckOutDate(checkoutDate);
						agreement.setDailyRentalCharge(f.format(charge.getDailyCharge()));
						agreement.setDueDate(dueDate);
						agreement.setChargeDays(chargableDays);
						agreement.setPreDiscountCharge(f.format(preDiscountCharge));
						agreement.setDiscountPercent(discountPercent);
						agreement.setDiscountAmount(f.format(discountAmount));
						agreement.setFinalCharge(f.format(finalCharge));
					} else {
						return null;
					}
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return agreement;
	}

	private Integer getChargableDays(Date checkoutDate, Date dueDate, Charge charge) {

		// excluded checkoutDate to calculate chargableDays
		checkoutDate = DateUtils.addDays(checkoutDate, 1);

		int chargableDays = DateUtils.getWeekDays(checkoutDate, dueDate);
		if (charge.getWeekendCharge()) {
			chargableDays += DateUtils.getWeekEnds(checkoutDate, dueDate);
		}
		if (!charge.getHolidayCharge()) {
			Integer holidays = getHolidaysCount(checkoutDate, dueDate, charge);
			chargableDays += holidays;
		}
		return chargableDays;

	}

	private Integer getHolidaysCount(Date checkoutDate, Date dueDate, Charge charge) {
		Integer holidaysCount = 0;
		Calendar cal = Calendar.getInstance();
		if ((checkoutDate.getMonth() + 1 <= 7 && dueDate.getMonth() + 1 >= 7)
				&& (checkoutDate.getDate() <= 4 && dueDate.getDate() >= 4)) {
			Date independenceDay = new Date();
			independenceDay.setDate(4);
			independenceDay.setMonth(6);
			independenceDay.setYear(checkoutDate.getYear());

			cal = Calendar.getInstance();
			cal.setTime(independenceDay);

			if ((cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (Calendar.DAY_OF_WEEK == Calendar.SUNDAY)) {
				if (charge.getWeekendCharge()) {
					holidaysCount++;
				}
			} else {
				holidaysCount++;
			}

		} else {
			// check for first monday date in september
			Date date = new Date();
			date.setMonth(8);
			date.setYear(checkoutDate.getYear());

			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			LocalDate firstMondayDate = localDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

			LocalDate startDate = checkoutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate endDate = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			// Check if the checkDate is between startDate and endDate
			boolean isBetween = firstMondayDate.isAfter(startDate) && firstMondayDate.isBefore(endDate);
			if (isBetween) {
				holidaysCount++;
			}
		}
		return holidaysCount;
	}

}
