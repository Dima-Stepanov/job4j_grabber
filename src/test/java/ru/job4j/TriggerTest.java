package ru.job4j;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TriggerTest {

    @Test
    public void someLogic() {
        Trigger trigger = new Trigger();
        assertThat(trigger.someLogic(), is(1));
    }
}