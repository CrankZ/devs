import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/**
 *
 */
public class EnhanceNameConvert extends INameConvert.DefaultNameConvert {

    public EnhanceNameConvert(StrategyConfig strategyConfig) {
        super(strategyConfig);
    }

    @Override
    public String entityNameConvert(TableInfo tableInfo) {
        return super.entityNameConvert(tableInfo) + "DO";
    }
}