package pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class TestEntity{

    private String name;
    private String pwd;
    private int num;
    private BigDecimal amt;
    private List<String> lsit;

}