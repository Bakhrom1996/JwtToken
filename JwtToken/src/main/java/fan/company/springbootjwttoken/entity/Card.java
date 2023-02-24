package fan.company.springbootjwttoken.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column (nullable = false)
    private String username;

    @NotNull
    @Column (nullable = false)
    private Long number;

    private double balance;

    private Date expiredDate;

    private boolean active=true;


}
