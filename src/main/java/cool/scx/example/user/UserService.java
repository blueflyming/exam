package cool.scx.example.user;

import cool.scx.annotation.ScxService;
import cool.scx.base.BaseModelService;
import cool.scx.bo.Query;
import cool.scx.example.dept.DeptService;
import cool.scx.example.role.RoleService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 核心用户 service
 *
 * @author scx567888
 * @version 1.1.2
 */
@ScxService
public class UserService extends BaseModelService<User> {

    /**
     * 部门 service
     */
    private final DeptService deptService;

    /**
     * 角色 service
     */
    private final RoleService roleService;

    /**
     * c
     *
     * @param deptService c
     * @param roleService c
     */
    public UserService(DeptService deptService, RoleService roleService) {
        this.deptService = deptService;
        this.roleService = roleService;
    }

    public User saveWithDeptAndRole(User user) {
        User save = super.save(user);
        deptService.saveDeptListWithUserID(save.id, user.deptIDs);
        roleService.saveRoleListWithUserID(save.id, user.roleIDs);
        return fillOne(save);
    }

    public User updateWithDeptAndRole(User user) {
        var update = super.update(user);
        //更新就是先删除再保存
        deptService.deleteByUserID(update.id);
        deptService.saveDeptListWithUserID(update.id, user.deptIDs);
        roleService.deleteByUserID(update.id);
        roleService.saveRoleListWithUserID(update.id, user.roleIDs);
        return fillOne(update);
    }

    public User fillOne(User old) {
        old.deptIDs = deptService.getDeptListByUser(old).stream().map(c -> c.id).collect(Collectors.toList());
        old.roleIDs = roleService.getRoleListByUser(old).stream().map(c -> c.id).collect(Collectors.toList());
        return old;
    }

    /**
     * {@inheritDoc}
     * <p>
     * 重写方法
     *
     * @param oldList a {@link Query} object
     * @return a {@link List} object
     */
    public List<User> fillList(List<User> oldList) {
        var userIDs = oldList.stream().map(user -> user.id).collect(Collectors.toList());
        var userDeptListFuture = CompletableFuture.supplyAsync(() -> deptService.getUserDeptByUserIDs(userIDs));
        var userRoleListFuture = CompletableFuture.supplyAsync(() -> roleService.getUserRoleByUserIDs(userIDs));
        try {
            var userDeptList = userDeptListFuture.get();
            var userRoleList = userRoleListFuture.get();
            return oldList.stream().peek(item -> {
                item.deptIDs = userDeptList.stream().filter(userDept -> userDept.userID.equals(item.id)).map(deptItem -> deptItem.deptID).collect(Collectors.toList());
                item.roleIDs = userRoleList.stream().filter(userRole -> userRole.userID.equals(item.id)).map(deptItem -> deptItem.roleID).collect(Collectors.toList());
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return oldList;
        }
    }

}
