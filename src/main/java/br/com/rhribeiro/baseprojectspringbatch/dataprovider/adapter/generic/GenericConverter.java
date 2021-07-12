package br.com.rhribeiro.baseprojectspringbatch.dataprovider.adapter.generic;

import br.com.rhribeiro.baseprojectspringbatch.error.exception.InternalServerErrorException;
import br.com.rhribeiro.baseprojectspringbatch.utils.StaticContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Class Exam Converter
 *
 * @author Renan Henrique Ribeiro
 * @since 11/07/2021
 */
@Slf4j
@Component
public class GenericConverter {

    @Autowired
    private MessageSource messageSource;

    private ModelMapper modelMapper = new ModelMapper();

    public <S, T> List<T> converterListToList(List<S> source, Class<T> outputClass) {
        if (null == source || source.isEmpty())
            throw new InternalServerErrorException(messageSource.getMessage("message.internal.server.error.list.converter", null, Locale.getDefault()));
        return source.stream().map(entity -> modelMapper.map(entity, outputClass)).collect(Collectors.toList());
    }

    public <S, T> T converterObjectToObject(S source, Class<T> outPutClass) {
        if (null == source)
            throw new InternalServerErrorException(messageSource.getMessage("message.internal.server.error.object.converter", null, Locale.getDefault()));
        return modelMapper.map(source, outPutClass);
    }
}
