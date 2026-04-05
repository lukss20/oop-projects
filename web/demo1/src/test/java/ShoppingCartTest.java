import com.example.demo1.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

    private ShoppingCart cart;
    @BeforeEach
    public void prepare() {
        cart = new ShoppingCart();
    }
    @Test
    void addItemTest() {
        cart.addItem("item1", "2");
        assertEquals(2, cart.shoppingcart.get("item1"));
    }

    @Test
    void addItemTest2() {
        cart.addItem("item1", "2");
        cart.addItem("item1", "3");
        assertEquals(5, cart.shoppingcart.get("item1"));
    }

    @Test
    void addItemTest3() {
        cart.addItem("item1", "1");
        cart.addItem("item2", "4");
        assertEquals(1, cart.shoppingcart.get("item1"));
        assertEquals(4, cart.shoppingcart.get("item2"));
    }
}
