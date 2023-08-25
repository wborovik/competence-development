package ru.axbit.service.exception;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.springframework.data.util.CastUtils;
import ru.axbit.vborovik.competence.faults.v1.BusinessFault;
import ru.axbit.vborovik.competence.faults.v1.ErrorMessage;

/**
 * Класс маппинга кастомных бизнес ошибок в SOAP структуру.
 */
public class UserServiceExceptionMapper {

    private static final String DOT = ".";

    public static Exception convert(BusinessException e, Class<?> clazz) {
        return getServiceException(clazz, e.getMessage(), getBusinessFault(e));
    }

    /**
     * Метод возвращает новый экземпляр класса, выбирая правильный конструктор на основе типов аргументов.
     *
     * @param clazz передается параметр типа {@link Class}.
     * @param args  передается массив аргументов.
     * @param <E>   параметризованный тип, наследуется от класса {@link Exception}.
     * @return Возвращается объект типа {@link Exception} и его наследники.
     */
    @SneakyThrows
    private static <E extends Exception> E getServiceException(Class<?> clazz, Object... args) {
        String packageName = PackageUtils.getPackageName(clazz);
        packageName = packageName.replace(DOT + "types", "");
        packageName += DOT + "Fault";
        Class<?> aClass = ClassUtils.getClass(packageName);

        return CastUtils.cast(ConstructorUtils.invokeConstructor(aClass, args));
    }

    /**
     * Метод возвращает бизнес ошибку с ее параметрами.
     *
     * @param e передается бизнес ошибка {@link BusinessException}
     * @return Возвращается SOAP тип {@link BusinessFault}, который содержит описание бизнес ошибки.
     */
    private static BusinessFault getBusinessFault(BusinessException e) {
        BusinessFault businessFault = new BusinessFault();
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setCode(e.getBusinessExceptionEnum().getCode());
        businessFault.setMessage(errorMessage);

        return businessFault;
    }
}
