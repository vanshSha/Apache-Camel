package com.wiretab;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto implements Serializable {

    private String transactionId;
    private String senderAccount;
    private String receiverAccount;
    private String amount;
    private String currency;
}
