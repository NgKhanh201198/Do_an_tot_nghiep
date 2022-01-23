package nguyenkhanh.backend.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import nguyenkhanh.backend.dto.UserCustomerDTO;
import nguyenkhanh.backend.entity.UserEntity;

@Component
public class UserConverter {
	public UserCustomerDTO entityToDTO(UserEntity user) {
		ModelMapper mapper = new ModelMapper();
		UserCustomerDTO userDTO = mapper.map(user, UserCustomerDTO.class);
		return userDTO;
	}

	public UserEntity dtoToEntity(UserCustomerDTO userDTO) {
		ModelMapper mapper = new ModelMapper();
		UserEntity user = mapper.map(userDTO, UserEntity.class);
		return user;
	}
}
