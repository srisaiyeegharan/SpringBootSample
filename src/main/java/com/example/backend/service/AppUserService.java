package com.example.backend.service;

import com.example.backend.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.messaging.AppUserDTO;
import com.example.backend.model.AppUser;

@Service
public class AppUserService {
	
	@Autowired
	private AppUserRepository userRepository;
	
	public AppUserDTO getUser(Long id)
	{
		return convertToAppUserDTOForClient(userRepository.findByUserId(id));
	}

	public String addUser(AppUserDTO user)
	{
		AppUser appUser = convertToAppUser(user);
		if (userRepository.findByUserEmail(appUser.getUserEmail()) == null)
		{
			userRepository.save(appUser);
			return "User Added";
		}
		return "User cannot be added";		
	}
	
	public String updateUser(AppUserDTO user, Long id)
	{
		AppUser appUser = convertToAppUser(user);
		AppUser tempUser = userRepository.findByUserId(id);
		appUser.setUserId(id);
		
		if(appUser.getUserEmail() == null)
		{
			appUser.setUserEmail(tempUser.getUserEmail());
		}
		if(appUser.getUserFirstName() == null)
		{
			appUser.setUserFirstName(tempUser.getUserFirstName());
		}
		if(appUser.getUserLastName() == null)
		{
			appUser.setUserLastName(tempUser.getUserLastName());
		}
		if(appUser.getUserPassword() == null)
		{
			appUser.setUserPassword(tempUser.getUserPassword());
		}
		
		userRepository.save(appUser);
		return "Updated";
	}
	
	// Converting from AppUser to AppUserDTO for Client
	private AppUserDTO convertToAppUserDTOForClient(AppUser user) {
		ModelMapper mm = new ModelMapper();
		mm.map(user, AppUserDTO.class);
		TypeMap<AppUser, AppUserDTO> tm = mm.getTypeMap(AppUser.class, AppUserDTO.class);
		//skip Password
		tm.addMappings(mapper -> mapper.skip(AppUserDTO::setUserPassword));
		AppUserDTO appUserDTO = mm.map(user, AppUserDTO.class);
		return appUserDTO;
	}
	
	// Converting from AppUser to AppUserDTO
	private AppUserDTO convertToAppUserDTO(AppUser user) {
		ModelMapper mm = new ModelMapper();
		AppUserDTO appUserDTO = mm.map(user, AppUserDTO.class);
		return appUserDTO;
	}
	
	// Converting from AppUserDTO to AppUser
	private AppUser convertToAppUser(AppUserDTO user) {
		ModelMapper mm = new ModelMapper();
//		mm.map(user, AppUser.class);
//		TypeMap<AppUserDTO,AppUser> tm = mm.getTypeMap(AppUserDTO.class, AppUser.class);
//		//skip setting user id
//		tm.addMappings(mapper -> mapper.skip(AppUser::setUserId));
		AppUser appUser = mm.map(user, AppUser.class);
		return appUser;
	}
}
