package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.game.models.Category;
import gulas.saveli.StadtLandFluss.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public Category convertCategoryString(String categoryName) {
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryName);
        if(categoryOptional.isPresent()) {
            return categoryOptional.get();
        }
        Category category = new Category(categoryName, false);
        categoryRepository.save(category);
        return category;
    }
}
