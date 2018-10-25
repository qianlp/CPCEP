package htos.business.action.project;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import htos.business.entity.project.Project;
import htos.common.util.StringUtil;
import htos.coresys.entity.Menu;
import htos.coresys.service.CommonFacadeService;
import htos.coresys.service.MenuService;
import htos.coresys.util.CommonUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author qianlp
 */

public class ProjectAction extends ActionSupport implements ModelDriven<Project> {

    private static final long serialVersionUID = 1L;
    private CommonFacadeService<Project> commonFacadeService;

    private MenuService menuService;
    private Project project ;


    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public CommonFacadeService<Project> getCommonFacadeService() {
        return commonFacadeService;
    }

    public void setCommonFacadeService(
            CommonFacadeService<Project> commonFacadeService) {
        this.commonFacadeService = commonFacadeService;
    }

    /**
     * struts2的驱动模型
     * 对实体自动赋值
     * @return
     */
    //批量保存或更新类型
    public String saveUpdateProject() throws Exception{
        if(!StringUtil.isEmpty(project.getUuid())){//更新
            commonFacadeService.saveOrUpdate(project);
        }else{//保存

//            project.setUpdateBy(project.getCreateBy());
            Menu menu = menuService.findOneMenuById("entityClsName", project.getClass().getSimpleName());
            commonFacadeService.save(project, menu.getUuid());
        }
        return "success";
    }
    //查询类型维护
    public String queryProjectData(){

        HttpServletRequest request = ServletActionContext.getRequest();
        String wfStatus = request.getParameter("wfStatus");
        List<Project> list = commonFacadeService.getListByProperty(Project.class.getSimpleName(),"wfStatus",wfStatus);
        CommonUtil.toJsonStr(ServletActionContext.getResponse(),list);
        return null;
    }

    public String queryProjectList(){


        List<Project> list = commonFacadeService.loadList(Project.class.getSimpleName());

        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

        for (Project project : list) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("id", project.getUuid());
            m.put("text", project.getProjectName());
            m.put("pid", "-1");
            m.put("type", "root");
            dataList.add(m);

        }

        CommonUtil.toJsonStr( ServletActionContext.getResponse(), dataList);

        return null;
    }


    @Override
    public Project getModel() {
        if(CommonUtil.isNullOrEmpty(project)){
            project= new Project();
        }
        return project;
    }
}
