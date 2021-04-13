package nguyenkhanh.backend.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import nguyenkhanh.backend.dto.UserDTO;
import nguyenkhanh.backend.entity.UserEntity;

@Component
public class UserConverter {
	public UserDTO entityToDTO(UserEntity user) {
		ModelMapper mapper = new ModelMapper();
		UserDTO userDTO = mapper.map(user, UserDTO.class);
		return userDTO;
	}

	public UserEntity dtoToEntity(UserDTO userDTO) {
		ModelMapper mapper = new ModelMapper();
		UserEntity user = mapper.map(userDTO, UserEntity.class);
		return user;
	}
}
