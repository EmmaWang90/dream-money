import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.pratice.stream.ParallelStream;
import org.junit.jupiter.api.Test;

@InjectService(ParallelStream.class)
public class TestParallelStream extends ServiceTestBase {
    @Service
    private ParallelStream parallelStream;

    @Test
    public void test() {
        parallelStream.practice();
    }
}
