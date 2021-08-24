package com.project.utils;

import com.project.entity.Cart;
import com.project.entity.CartDetail;
import com.project.entity.Wishlist;
import com.project.service.BannerService;
import com.project.service.CartService;
import com.project.service.CategoryService;
import com.project.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Component
public class MenuHandleInterceptor implements HandlerInterceptor {

    @Autowired
    CategoryService categoryService;

    @Autowired
    BannerService bannerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private WishlistService wishlistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute("menu", categoryService.findByIsDisplay(true));
        request.setAttribute("banners", bannerService.findAll());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            Optional<Cart> cart = cartService.findCartByUserUserName(authentication.getName());
            if (cart.isPresent()) {
                if (cart.get().getTotalAmount() != null)
                    request.setAttribute("amountIcon", cart.get().getTotalAmount());
                else
                    request.setAttribute("amountIcon", 0);
            }
            List<Wishlist> wishlists = wishlistService.findByUserUserName(authentication.getName());
            if (wishlists != null){
                request.setAttribute("amountWishlist", wishlists.size());
            }else{
                request.setAttribute("amountWishlist", 0);
            }
        }
        return true;
    }

}
