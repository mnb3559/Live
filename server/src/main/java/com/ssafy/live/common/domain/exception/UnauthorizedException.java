package com.ssafy.live.common.domain.exception;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(ErrorCode error) {
        super(error);
    }
}