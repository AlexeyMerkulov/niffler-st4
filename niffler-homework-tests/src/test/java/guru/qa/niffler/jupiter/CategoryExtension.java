package guru.qa.niffler.jupiter;

import guru.qa.niffler.api.CategoryApi;
import guru.qa.niffler.api.SpendClientProvider;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.List;
import java.util.Optional;

public class CategoryExtension implements BeforeEachCallback {

    private final CategoryApi categoryApi = new SpendClientProvider().getSpendClient().create(CategoryApi.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<GenerateCategory> category = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateCategory.class
        );

        if (category.isPresent()) {
            GenerateCategory categoryData = category.get();
            List<CategoryJson> categories = categoryApi.getCategories(categoryData.username()).execute().body();
            Optional<CategoryJson> existedCategoryJson = Optional.empty();
            if (categories != null) {
                existedCategoryJson = categories
                        .stream()
                        .filter(x -> categoryData.category().equals(x.category()))
                        .findFirst();
            }
            existedCategoryJson.ifPresent(categoryJson -> saveCategoryJsonToStore(extensionContext, categoryJson));
            if (existedCategoryJson.isEmpty()) {
                CategoryJson categoryJson = new CategoryJson(
                        null,
                        categoryData.category(),
                        categoryData.username()
                );
                CategoryJson created = categoryApi.addCategory(categoryJson).execute().body();
                saveCategoryJsonToStore(extensionContext, created);
            }
        }
    }

    private void saveCategoryJsonToStore(ExtensionContext extensionContext, CategoryJson categoryJson) {
        extensionContext.getStore(SpendExtension.NAMESPACE)
                .put("category", categoryJson);
    }
}
