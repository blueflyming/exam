package cool.scx.example.user;

import cool.scx.annotation.FromBody;
import cool.scx.annotation.ScxMapping;
import cool.scx.base.BaseModel;
import cool.scx.enumeration.HttpMethod;
import cool.scx.ext.crud.CRUDHelper;
import cool.scx.ext.crud.CRUDOrderByBody;
import cool.scx.ext.crud.CRUDWhereBody;
import cool.scx.vo.Json;

import java.util.List;

@ScxMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ScxMapping(value = "list", method = {HttpMethod.POST})
    public Json list(@FromBody(value = "pagination.limit", required = false) Integer limit, @FromBody(value = "pagination.page", required = false) Integer page, @FromBody(value = "orderByBodyList", required = false) List<CRUDOrderByBody> orderByBodyList, @FromBody(value = "whereBodyList", required = false) List<CRUDWhereBody> whereBodyList) {
        var query = CRUDHelper.getQuery(User.class, limit, page, orderByBodyList, whereBodyList);
        var list = userService.fillList(userService.list(query));
        long total = userService.count(query);
        return Json.ok().put("items", list).put("total", total);
    }

    //设置空路由有以下两种方法
    // useNameAsUrl = false ,或 value = "/"
    @ScxMapping(useNameAsUrl = false, method = {HttpMethod.POST})
    public Json save(@FromBody(useAllBody = true) User user) {
        BaseModel savedModel = this.userService.saveWithDeptAndRole(user);
        return Json.ok().put("item", savedModel);
    }

    @ScxMapping(value = "/", method = {HttpMethod.PUT})
    public Json update(@FromBody(useAllBody = true) User user) {
        BaseModel updatedModel = this.userService.updateWithDeptAndRole(user);
        return Json.ok().put("item", updatedModel);
    }

}
