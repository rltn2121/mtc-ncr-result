package result.apis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MtcResultRequest {
    private int sno;
    private String key;
    private String acno;
    private String trxdt;
    private String curC;
    private int upmuG;
    private int aprvSno;
    private int trxAmt;
    private int nujkJan;
}
