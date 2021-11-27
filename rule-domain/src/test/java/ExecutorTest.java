import com.devs.devs.executor.Creator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ExecutorTest {

    @Test
    public void executeTest() {
        String expression = "StringMethod.equals(a,b)";
        Map<String, Object> env = new HashMap<>();
        env.put("a", "hello");
        env.put("b", "hello");

        AviatorEvaluatorInstance engine = Creator.getEngine();

        Object obj = engine.execute(expression, env);
        System.out.println(obj);

    }
}
