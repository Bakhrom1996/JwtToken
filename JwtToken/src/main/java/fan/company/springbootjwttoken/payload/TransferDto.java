package fan.company.springbootjwttoken.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferDto {

    private Long fromCardId;

    private Long toCardId;

    private double summaToTransfer;

}
