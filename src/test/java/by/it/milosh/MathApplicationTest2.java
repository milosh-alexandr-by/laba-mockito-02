package by.it.milosh;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
/* зависимость нужна, чтобы использовать given-when-then */
import static org.mockito.BDDMockito.*;

import static org.mockito.Mockito.*;
/*
* Здесь используется второй способ (без аннотаций) для создания мок-объектов
* */
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTest2 {

    private MathApplication mathApplication;
    private CalculatorService calcService;

    @Before
    public void setUp() {
        mathApplication = new MathApplication();
        calcService = mock(CalculatorService.class);
        mathApplication.setCalculatorService(calcService);
    }

    @Test
    public void testAddAndSubtract() {
        when(calcService.add(20.0,10.0)).thenReturn(30.0);

        when(calcService.subtract(20.0,10.0)).thenReturn(10.0);

        assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

        assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

        verify(calcService).add(20.0,10.0);
        verify(calcService).subtract(20.0,10.0);
    }

    @Test
    public void testAddAndSubtract2(){

        when(calcService.add(20.0,10.0)).thenReturn(30.0);
        when(calcService.subtract(20.0,10.0)).thenReturn(10.0);

        Assert.assertEquals(mathApplication.add(20.0, 10.0),30.0,0);
        Assert.assertEquals(mathApplication.subtract(20.0, 10.0),10.0,0);

        InOrder inOrder = inOrder(calcService);  /* позволяет определить в каком порядке должны были вызываться методы */

        //inOrder.verify(calcService).add(20.0, 10.0);  /* в этом случае будет true */
        inOrder.verify(calcService).subtract(20.0,10.0);  /* в данном случае выдаст ошибку*/
        inOrder.verify(calcService).add(20.0,10.0);
    }

    /**
     * когда сетаем методу значение - не просто пишем thenReturn(30.0); а создаём целый метод
     */
    @Test
    public void testAdd2(){

        //add the behavior to add numbers
        when(calcService.add(20.0,10.0)).thenAnswer(new Answer<Double>() {

            @Override
            public Double answer(InvocationOnMock invocation) throws Throwable {
                //get the arguments passed to mock
                Object[] args = invocation.getArguments();

                //get the mock
                Object mock = invocation.getMock();

                //return the result
                return 30.0;
            }
        });

        //test the add functionality
        assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);
    }

    @Test
    public void testAdd3(){

        //Given
        given(calcService.add(20.0,10.0)).willReturn(30.0);

        //when
        double result = calcService.add(20.0,10.0);

        //then
        Assert.assertEquals(result,30.0,0);
    }

    @Test
    public void testAddAndSubtract3(){

        when(calcService.add(20.0,10.0)).thenReturn(30.0);
        when(calcService.subtract(20.0,10.0)).thenReturn(10.0);


        Assert.assertEquals(mathApplication.subtract(20.0, 10.0),10.0,0);
        Assert.assertEquals(mathApplication.add(20.0, 10.0),30.0,0);

        //verify call to add method to be completed within 100 ms
        verify(calcService, timeout(1)).add(20.0,10.0);    /* проверяет, вызван ли метод в оговоренный временной интервал. */

        //invocation count can be added to ensure multiplication invocations
        //can be checked within given timeframe
        verify(calcService, timeout(100).times(1)).subtract(20.0,10.0);
    }

}
