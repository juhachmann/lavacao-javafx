package ifsc.poo.lavacao.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;


import static org.junit.jupiter.api.Assertions.*;

class JavaNumberImmutabilityTest {

    @Test
    void wrapersAreImmutable() {
        Integer num = 5;

        System.out.println(num);

        num.longValue();
        assertEquals(5, num);
        assertInstanceOf(Integer.class, num);

        Long newNum = num.longValue();
        assertInstanceOf(Long.class, newNum);

    }

    @Test
    void bigDecimalIsImmutable() {

        BigDecimal num = new BigDecimal(1000);
        num.add(new BigDecimal(5000));
        assertEquals(1000.0, num.doubleValue());

        num = num.add(new BigDecimal(5000));
        assertEquals(6000.0, num.doubleValue());

    }

    @Test
    void bigIntegerIsImmutable() {

        BigInteger num = BigInteger.valueOf(1000);
        num.add(BigInteger.valueOf(5000));
        assertEquals(1000, num.longValue());

        num = num.add(BigInteger.valueOf(5000));
        assertEquals(6000, num.longValue());
    }

    @Test
    void atomicsAreMutable() {
        AtomicInteger num = new AtomicInteger();
        assertEquals(0, num.get());
        num.incrementAndGet();
        assertEquals(1, num.get());
    }


}
