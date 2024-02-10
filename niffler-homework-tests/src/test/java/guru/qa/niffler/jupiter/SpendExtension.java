package guru.qa.niffler.jupiter;

import guru.qa.niffler.api.SpendClientProvider;
import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class SpendExtension implements BeforeEachCallback {

  public static final ExtensionContext.Namespace NAMESPACE
      = ExtensionContext.Namespace.create(SpendExtension.class);

  private final SpendApi spendApi = new SpendClientProvider().getSpendClient().create(SpendApi.class);

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {
    Optional<GenerateSpend> spend = AnnotationSupport.findAnnotation(
        extensionContext.getRequiredTestMethod(),
        GenerateSpend.class
    );

    if (spend.isPresent()) {
      GenerateSpend spendData = spend.get();
      CategoryJson categoryJson = extensionContext.getStore(NAMESPACE).get("category", CategoryJson.class);
      if (categoryJson == null) {
        throw new RuntimeException("Не было создано категории трат");
      }
      if (!Objects.equals(categoryJson.username(), spendData.username())) {
        throw new RuntimeException("Категория трат была создана для другого пользователя");
      }
      SpendJson spendJson = new SpendJson(
          null,
          new Date(),
          categoryJson.category(),
          spendData.currency(),
          spendData.amount(),
          spendData.description(),
          spendData.username()
      );

      SpendJson created = spendApi.addSpend(spendJson).execute().body();
      extensionContext.getStore(NAMESPACE)
          .put("spend", created);
    }
  }
}
