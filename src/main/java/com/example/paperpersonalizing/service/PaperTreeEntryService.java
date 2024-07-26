package com.example.paperpersonalizing.service;

import com.example.paperpersonalizing.entity.CategoryBo;
import com.example.paperpersonalizing.entity.CategoryPo;
import com.example.paperpersonalizing.entity.PaperBo;
import com.example.paperpersonalizing.entity.PaperPo;
import com.example.paperpersonalizing.entity.papertreeComposite.Paper;
import com.example.paperpersonalizing.entity.papertreeComposite.PaperAlerting;
import com.example.paperpersonalizing.entity.papertreeComposite.PaperTree;
import com.example.paperpersonalizing.mapper.AlertPaperMapper;
import com.example.paperpersonalizing.mapper.PaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaperTreeEntryService {
    @Autowired
    private AlertPaperMapper alertPaperMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private ParseService parseService;

    // 从用户端删除用户选择拒绝接受端alert文章
    public void deleteNotAlertPaper(int userId, int paperId){
        alertPaperMapper.insertNotPaperByUserIdPaperId(userId, paperId);

    }

    // 添加用户接受端alert文章
    public void addAlertPaper(int userId,int paperId){
        alertPaperMapper.insertAlertedPaperByUserIdPaperIdCategoryId(paperId, userId, paperMapper.getPaperCategoryIdByPaperId(paperId));

    }

    public PaperTree buildPaperTreeCategory (String userName, HashMap<Integer, List<CategoryPo>> map,
                                              HashMap<Integer, List<PaperPo>> paperMap, HashSet<Integer> alertingSet){
        // BFS
        Queue<PaperTree> queue=new PriorityQueue<>();
        CategoryBo root=new CategoryBo(userName,null);
        PaperTree rootPaperTree=new PaperTree(root);
        queue.offer(rootPaperTree);
        while (!queue.isEmpty()){
            PaperTree paperTree=queue.poll();
            List<CategoryPo> list=map.get(paperTree.getCategoryBo().getId());
            List<PaperPo> paperList=paperMap.get(paperTree.getCategoryBo().getId());
            for (PaperPo paperPo : paperList) {
                //如果是alert过去但是用户还没确认
                if(alertingSet.contains(paperPo.getPaperId())){
                    PaperAlerting paperAlerting=new PaperAlerting(new PaperBo(paperPo));
                    paperTree.addPaperTreeEntry(paperAlerting);
                }else{//一般情况
                    Paper paper=new Paper(new PaperBo(paperPo));
                    paperTree.addPaperTreeEntry(paper);
                }

            }
            for (CategoryPo categoryPo : list) {
                PaperTree childPaperTree=new PaperTree(new CategoryBo(categoryPo));
                queue.offer(childPaperTree);
                paperTree.addPaperTreeEntry(childPaperTree);
            }
        }
        return rootPaperTree;
    }
}
