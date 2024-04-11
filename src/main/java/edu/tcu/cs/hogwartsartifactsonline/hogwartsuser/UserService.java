package edu.tcu.cs.hogwartsartifactsonline.hogwartsuser;

import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<HogwartsUser> findAll() {
        return this.userRepository.findAll();
    }
    public HogwartsUser findById(Integer userId){
        return this.userRepository.findById(userId)
                .orElseThrow(()->
                        new ObjectNotFoundException("user", userId));
    }
    public HogwartsUser save(HogwartsUser newUser){
        return this.userRepository.save(newUser);
    }
    public HogwartsUser update(Integer userId, HogwartsUser update){
        HogwartsUser oldUser = this.userRepository.findById(userId)
                .orElseThrow(()-> new ObjectNotFoundException("user", userId));
        oldUser.setUsername(update.getUsername());
        oldUser.setEnabled(update.isEnabled());
        oldUser.setRoles(update.getRoles());
        return this.userRepository.save(oldUser);
    }
    public void delete(Integer userId){
        this.userRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundException("user",userId));
        this.userRepository.deleteById(userId);
    }
}
