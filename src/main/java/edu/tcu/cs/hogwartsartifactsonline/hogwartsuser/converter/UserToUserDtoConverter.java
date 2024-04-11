package edu.tcu.cs.hogwartsartifactsonline.hogwartsuser.converter;

import org.springframework.core.convert.converter.Converter;
import edu.tcu.cs.hogwartsartifactsonline.hogwartsuser.HogwartsUser;
import edu.tcu.cs.hogwartsartifactsonline.hogwartsuser.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<HogwartsUser, UserDto>{
    @Override
    public UserDto convert(HogwartsUser source) {
        return new UserDto(source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles());
    }
}
