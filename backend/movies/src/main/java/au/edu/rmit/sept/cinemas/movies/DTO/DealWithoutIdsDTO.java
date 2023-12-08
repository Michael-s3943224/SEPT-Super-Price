package au.edu.rmit.sept.cinemas.movies.DTO;

import java.math.BigDecimal;
import java.sql.Date;

public record DealWithoutIdsDTO(
        BigDecimal reductionValue,
        String reductionType,
        Date fromDate,
        Date toDate
) { }
