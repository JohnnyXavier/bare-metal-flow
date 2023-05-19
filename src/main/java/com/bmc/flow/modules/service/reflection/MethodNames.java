package com.bmc.flow.modules.service.reflection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MethodNames {
    SET_ACTION_TO_TAKE("setActionToTake"),
    SET_AVATAR("setAvatar"),
    SET_CALL_SIGN("setCallSign"),
    SET_CLOSE_DATE("closeDate"),
    SET_COLOR_HEX("setColorHex"),
    SET_COMMENT("setComment"),
    SET_COMPLETED_DATE("setCompletedDate"),
    SET_COVER_IMAGE("setCoverImage"),
    SET_DESCRIPTION("setDescription"),
    SET_DUE_DATE("setDueDate"),
    SET_DURATION_IN_MIN("setDurationInMin"),
    SET_EMAIL("setEmail"),
    SET_END_DATE("endDate"),
    SET_FROM_DATE("fromDate"),
    SET_GOAL("goal"),
    SET_HOURS_A_DAY("setHoursADay"),
    SET_IS_FAVORITE("setIsFavorite"),
    SET_LEVEL("setLevel"),
    SET_NAME("setName"),
    SET_PERCENTAGE("setPercentage"),
    SET_START_DATE("startDate"),
    SET_VOTES("setVotes");

    private final String methodName;
}
