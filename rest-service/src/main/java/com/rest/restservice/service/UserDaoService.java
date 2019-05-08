package com.rest.restservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rest.restservice.controller.model.User;
import com.rest.restservice.exception.NoUserFoundException;
@Service(value="userDaoService")
public class UserDaoService {
private static List<User>userList=new ArrayList<>();
static {
	userList.add(new User(1,"lakhan" , new Date()));
	userList.add(new User(2,"lakhan singh" , new Date()));
	userList.add(new User(3,"lakhan kumar " , new Date()));
	userList.add(new User(4,"lakhan cheyon" , new Date()));
}
public List<User>findAll(){
	return userList;
}
public User getuser(int id) {
	List<User>uu=userList.stream().filter(u->u.getUserId()==id).collect(Collectors.toList());
	
	 if(uu.size()>0) {
		 return uu.get(0); 
		 
	 }else {
		throw  new NoUserFoundException("No user found");
	 }
	
}
public List<User>saveUser(User u){
	u.setUserId(userList.size());;
	userList.add(u);
	return userList;
}
public List<User>deleteUser(int id){
	userList= userList.stream().filter(u->u.getUserId()!=id).collect(Collectors.toList());
return userList;
}
}
