package jpabook.jpashop.controller;

import java.util.List;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm()); // 빈 화면을 가지고 감, validation을 위해 MemberForm을 넘겨줌
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items); // items를 넘겨줌
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) { // PathVariable은 URL에 있는 값을 가져옴
        Book item = (Book) itemService.findOne(itemId); // itemService.findOne(itemId)는 item을 찾아서 반환
        BookForm form = new BookForm(); // BookForm을 만들어서
        form.setId(item.getId()); // BookForm에 item의 id를 넣어줌
        form.setName(item.getName()); // BookForm에 item의 name을 넣어줌
        form.setPrice(item.getPrice()); // BookForm에 item의 price를 넣어줌
        form.setStockQuantity(item.getStockQuantity()); // BookForm에 item의 stockQuantity를 넣어줌
        form.setAuthor(item.getAuthor()); // BookForm에 item의 author를 넣어줌
        form.setIsbn(item.getIsbn()); // BookForm에 item의 isbn을 넣어줌
        model.addAttribute("form", form); // model에 form을 넣어줌
        return "items/updateItemForm"; // updateItemForm.html로 넘어감
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form, @PathVariable String itemId) { // PathVariable은 URL에 있는 값을 가져옴
//        Book book = new Book(); // Book을 만들어서
//        book.setId(form.getId()); // Book에 form의 id를 넣어줌
//        book.setName(form.getName()); // Book에 form의 name을 넣어줌
//        book.setPrice(form.getPrice()); // Book에 form의 price를 넣어줌
//        book.setStockQuantity(form.getStockQuantity()); // Book에 form의 stockQuantity를 넣어줌
//        book.setAuthor(form.getAuthor()); // Book에 form의 author를 넣어줌
//        book.setIsbn(form.getIsbn()); // Book에 form의 isbn을 넣어줌
//        itemService.saveItem(book); // itemService.saveItem(book)을 하면 book이 저장됨

        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items"; // items로 넘어감
    }
}
