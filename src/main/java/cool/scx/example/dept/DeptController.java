package cool.scx.example.dept;

import cool.scx.annotation.FromBody;
import cool.scx.annotation.ScxMapping;
import cool.scx.enumeration.HttpMethod;
import cool.scx.vo.Json;

/**
 * <p>DeptController class.</p>
 *
 * @author scx567888
 * @version $Id: $Id
 */
@ScxMapping("api/dept")
public class DeptController {

    private final DeptService deptService;

    /**
     * <p>Constructor for DeptController.</p>
     *
     * @param deptService a {@link cool.scx.example.dept.DeptService} object
     */
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * <p>deleteDeptWithChildren.</p>
     *
     * @param id a {@link java.lang.Long} object
     * @return a {@link cool.scx.vo.Json} object
     */
    @ScxMapping(method = HttpMethod.DELETE)
    public Json deleteDeptWithChildren(@FromBody Long id) {
        deptService.deleteDeptWithChildren(id);
        return Json.ok();
    }

}
