package by.it.milosh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {

    /* добавил это, потмоу что assertequals(double, double) is deprecated */
    /* see https://stackoverflow.com/questions/5686755/meaning-of-epsilon-argument-of-assertequals-for-double-values*/
    private static final double DELTA = 1e-15;

    @InjectMocks
    MathApplication mathApplication = new MathApplication();

    @Mock
    CalculatorService calcService;

    @Test
    public void testAdd() {
        when(calcService.add(10.0, 20.0)).thenReturn(30.0);  /* сетаем значение методу, который вызваем в when()*/
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, DELTA);  /* сравниваем значение, полученное методом, с нашим значением, которое ожидаем */
        verify(calcService).add(10.0, 20.0);  /* проверяем, использовал ли данный мок (calcService) метод add() с данными параметрами (10.0, 20.0) */
    }

}
