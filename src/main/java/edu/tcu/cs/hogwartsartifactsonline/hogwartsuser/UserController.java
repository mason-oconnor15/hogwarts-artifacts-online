package edu.tcu.cs.hogwartsartifactsonline.hogwartsuser;

import edu.tcu.cs.hogwartsartifactsonline.system.Result;
import edu.tcu.cs.hogwartsartifactsonline.system.StatusCode;
import edu.tcu.cs.hogwartsartifactsonline.hogwartsuser.converter.UserDtoToUserConverter;
import edu.tcu.cs.hogwartsartifactsonline.hogwartsuser.converter.UserToUserDtoConverter;
import edu.tcu.cs.hogwartsartifactsonline.hogwartsuser.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {
    private final UserService userService;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final UserDtoToUserConverter userDtoToUserConverter;

    public UserController(UserService userService, UserToUserDtoConverter userToUserDtoConverter, UserDtoToUserConverter userDtoToUserConverter) {
        this.userService = userService;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }
    @GetMapping()
    public Result getAllUsers(){
        List<HogwartsUser> foundUsers = this.userService.findAll();
        List<UserDto>  userDtos = foundUsers.stream().map(this.userToUserDtoConverter::convert).collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", userDtos);
    }
    @GetMapping("/{userId}")
    public Result getUserById(@PathVariable Integer userId){
        HogwartsUser user = this.userService.findById(userId);
        UserDto userDto = this.userToUserDtoConverter.convert(user);
        return  new Result(true, StatusCode.SUCCESS, "Find One Success", userDto);
    }
    @PostMapping()
    public Result addUser(@Valid @RequestBody HogwartsUser newHogwartsUser){
        HogwartsUser savedUser = this.userService.save(newHogwartsUser);
        UserDto savedUserDto = this.userToUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedUserDto);
    }
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto){
        HogwartsUser update = this.userDtoToUserConverter.convert(userDto);
        HogwartsUser updatedUser = this.userService.update(userId, update);
        UserDto updatedDto  = this.userToUserDtoConverter.convert(updatedUser);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedDto);
    }
    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable Integer userId){
        this.userService.delete(userId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
