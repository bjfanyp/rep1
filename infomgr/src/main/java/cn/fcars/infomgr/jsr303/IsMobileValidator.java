package cn.fcars.infomgr.jsr303;

import com.alibaba.druid.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @create 2018-08-08 下午10:02
 * @desc  自定义校验器的校验类
 **/
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required =false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    //判断数据是否合法
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            //调用第3步 中的校验类的校验方法进行判断是否是手机号
            return ValidatorUtil.isMobile(value);
        }else {
            if(StringUtils.isEmpty(value)){
                return true;
            }else{
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}