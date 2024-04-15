package br.csi.porkManagerApi.utils.enumUtils;


import br.csi.porkManagerApi.models.Usuario;

public class EnumUtils {
    public static <T extends Enum<T>> T stringToEnum(Class<T> enumClass, String value) {
        T enumArr = null;
        for (T enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                enumArr = enumValue;
            }
        }
        return enumArr;
    }
}
