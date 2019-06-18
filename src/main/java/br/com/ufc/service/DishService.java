package br.com.ufc.service;

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
		String imageName = null;
		String imageExt = FilenameUtils.getExtension(image.getOriginalFilename());

		if (dish.getImage() == null) {
			imageName = String.valueOf(System.currentTimeMillis());
		} else {
			imageName = image.getName();
		}

		dish.setImage(imageName + "." + imageExt);
		dishRepository.save(dish);

		String path = "images/" + imageName + "." + imageExt;
		FileUtils.saveImage(path, image);
	}

	public List<Dish> list() {
		return dishRepository.findAll();
	}

	public Dish findById(Long id) {
		return dishRepository.getOne(id);
	}

	public void delete(Long id) {
		dishRepository.deleteById(id);
	}

}
