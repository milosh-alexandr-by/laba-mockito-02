package by.it.milosh;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTest {

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
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, DELTA);
        //verify(calcService).add(10.0, 20.0);  /* проверяем, использовал ли данный мок (calcService) метод add() с данными параметрами (10.0, 20.0), но только один раз */
        verify(calcService, times(2)).add(10.0, 20.0);  /* вернёт true если calcSrvice.add(10.0, 20.0) вызывался два раза */
        verify(calcService, never()).multiply(10.0, 20.0);  /* вернёт true если calcSrvice.multiply(10.0,20.0) не вызывался ни разу */
        when(calcService.subtract(20.0,10.0)).thenReturn(10.00);
        assertEquals(mathApplication.subtract(20.0, 10.0),10.0,0.0);
        verify(calcService, atLeastOnce()).subtract(20.0, 10.0);  /* вернёт true если метод вызывался хотя бы один раз */
        verify(calcService, atLeast(2)).add(10.0, 20.0);  /* вернёт true если метод вызывался как минимум 2 раза */
        verify(calcService, atMost(5)).add(10.0, 20.0);  /* вернёт true если метод вызывался максимум 3 раза */
    }

    //@Test(expected = RuntimeException.class)  /* если записать так - то тест проходит */
    @Test  /* если записать так - то тест НЕ проходит */
    public void testAddExc() {
        doThrow(new RuntimeException("hello from runtimeException")).when(calcService).add(10.0, 20.0);  /* вернёт runtimeException */
        assertEquals(mathApplication.add(10.0, 20.0), 30.0, DELTA);  /* сравниваем значение, полученное методом, с нашим значением, которое ожидаем */

    }
}
