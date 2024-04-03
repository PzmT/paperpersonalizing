package com.example.paperpersonalizing.entity.papertreeComposite;


import com.example.paperpersonalizing.entity.PaperBo;

import java.util.List;

public class Paper extends PaperTreeEntry {

    private PaperBo paperBo;
    public Paper(PaperBo paperBo) {
        this.paperBo=paperBo;
    }

    public PaperBo getPaperBo() {
        return paperBo;
    }

    public void setPaperBo(PaperBo paperBo) {
        this.paperBo = paperBo;
    }
}
