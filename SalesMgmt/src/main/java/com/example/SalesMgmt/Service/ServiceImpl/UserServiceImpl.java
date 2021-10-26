package com.example.SalesMgmt.Service.ServiceImpl;

import antlr.Token;
import com.example.SalesMgmt.DTO.TokenDTO;
import com.example.SalesMgmt.DTO.UserDTO;
import com.example.SalesMgmt.Entity.Role;
import com.example.SalesMgmt.Entity.User;
import com.example.SalesMgmt.Entity.UserRole;
import com.example.SalesMgmt.Repository.RoleRepository;
import com.example.SalesMgmt.Repository.UserRepository;
import com.example.SalesMgmt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO addUser(UserDTO user) {
        User user1 = new User();
        user1.setFirstname(user.getFirstname());
        user1.setUsername(user.getUsername());
        user1.setLastname(user.getLastname());
        user1.setMobile_no(user.getMobile_no());
        user1.setIs_active(user.getIs_active());
        user1.setPassword(user.getPassword());
        user1.setIs_deleted(user.getIs_deleted());
        List<UserRole> roleList = new LinkedList<>();
        user.getRoleList().stream().forEachOrdered(action->{
            UserRole userRole = new UserRole();
            Optional<Role> role = roleRepository.findById(action.getId());
            userRole.setRole(role.get());
            userRole.setUser(user1);
            roleList.add(userRole);
        });
        user1.setRoleList(roleList);
        try {
            userRepository.save(user1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }



    @Override
    public Page<User> listAllDetails(int pageNo , int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(Sort.Direction.DESC,sortBy));
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    @Override
    public UserDTO updateProductDetails(UserDTO user) {
        User user1 = new User();
        user1.setId(user.getId());
        user1.setFirstname(user.getFirstname());
        user1.setUsername(user.getUsername());
        user1.setLastname(user.getLastname());
        user1.setMobile_no(user.getMobile_no());
        user1.setIs_active(user.getIs_active());
        user1.setPassword(user.getPassword());
        user1.setIs_deleted(user.getIs_deleted());
        userRepository.save(user1);
        return null;
    }
    public User getProductDetailsByID(int userid) {
        Optional<User> user = userRepository.findById(userid);
        return user.get();
    }
        @Override
    public String deleteDetailsById(int id) {
        userRepository.deleteById(id);
        return "successfully deleted";
    }

/*    public UserDetails loadByUsername(String username) {
//        Optional<User> user = userRepository.findById()
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){
            return (UserDetails) user.get();
        }
        return null;
    }*/



    @Override
    public TokenDTO login(TokenDTO tokenDTO) {
        Optional<User> Obj = userRepository.findByUsernameAndPassword(tokenDTO.getUsername(),tokenDTO.getPassword());
        try
        {
            if(Obj.isPresent())
            {
               /* String tokens = TokenGeneration.generateToken("Admin",Obj.get().getUsername(),Obj.get().getPassword());
                tokenDTO.setToken(tokens);
*/
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tokenDTO;
    }

}
