package controllers;

import play.mvc.*;
import play.data.*;

import models.*;
import views.html.products.details;
import views.html.products.list;

import javax.inject.Inject;
import java.util.List;

public class Products extends Controller {

    private FormFactory formFactory;

    @Inject
    public Products(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result list() {
        List<Product> products = Product.findAll();
        return ok(list.render(products));
    }


    public Result newProduct() {
        return ok(details.render(formFactory.form(Product.class)));
    }

    public Result details(String ean) {
        final Product product = Product.findByEan(ean);
        if (product == null) {
            return notFound(String.format("Product %s does not exist.", ean));
        }
        Form<Product> productForm = formFactory.form(Product.class);
        Form<Product> filledForm = productForm.fill(product);
        return ok(details.render(filledForm));
    }

    public Result save() {
        Form<Product> boundForm = formFactory.form(Product.class).bindFromRequest();
        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(details.render(boundForm));
        }
        Product product = boundForm.get();
        product.save();
        flash("success", String.format("Successfully added product %s", product));
        return redirect(routes.Products.list());
    }

    public Result delete(String ean) {
        final Product product = Product.findByEan(ean);
        if (product == null) {
            return notFound(String.format("Product %s does not exist.", ean));
        }
        Product.remove(product);
        return redirect(routes.Products.list());
    }
}
