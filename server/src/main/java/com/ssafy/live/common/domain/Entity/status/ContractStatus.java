package com.ssafy.live.common.domain.Entity.status;

import com.amazonaws.services.kms.model.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ContractStatus {

    CONTRACT_APPROVING(0), // 승인 중
    CONTRACT_PROCESSING(1), // 계약 진행 중
    CONTRACT_COMPLETE(2); // 계약 완료
    private int value;
    public static ContractStatus ofValue(int value) {
        return Arrays.stream(ContractStatus.values())
                .filter(v-> v.getValue() == value)
                .findAny()
                .orElseThrow(() -> new NotFoundException(String.format("상태코드에 [%s]가 존재하지 않습니다.", value)));
    }
}
