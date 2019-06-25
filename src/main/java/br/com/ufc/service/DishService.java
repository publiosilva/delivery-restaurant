package br.com.ufc.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.ufc.model.Dish;
import br.com.ufc.repository.DishRepository;
import br.com.ufc.util.FileUtils;

@Service
public class DishService {

	@Autowired
	private DishRepository dishRepository;

	public void save(Dish dish, MultipartFile image) {
		if (image.isEmpty() == false) {
			String imageName = String.valueOf(System.currentTimeMillis());
			String imageExt = FilenameUtils.getExtension(image.getOriginalFilename());

//			Delete old image
			new File("images/" + dish.getImage()).delete();

//			Save new image
			FileUtils.saveImage("images/" + imageName + "." + imageExt, image);

//			Set new image
			dish.setImage(imageName + "." + imageExt);
		}

		dishRepository.save(dish);
	}

	public List<Dish> list() {
		return dishRepository.findAll();
	}

	public Dish findById(Long id) {
		return dishRepository.getOne(id);
	}

	public void delete(Long id) {
//		Delete old image
		Dish dish = findById(id);
		new File("images/" + dish.getImage()).delete();

		dishRepository.deleteById(id);
	}

}
