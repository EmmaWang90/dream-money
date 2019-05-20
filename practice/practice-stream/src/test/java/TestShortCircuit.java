import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.pratice.stream.ShortCircuit;
import org.junit.jupiter.api.Test;

@InjectService(ShortCircuit.class)
public class TestShortCircuit extends ServiceTestBase {
    @Service
    private ShortCircuit shortCircuit;

    @Test
    public void test() {
        shortCircuit.practice();
    }
}
