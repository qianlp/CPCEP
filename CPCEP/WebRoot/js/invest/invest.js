window.investMsg = {
    subjectCodeLevel1:[ '一','','二','三','四','五','六','七','八','九','十','十一','十二','十三','十四','十五','十六','十七','十八','十九'],
    subjectCodeLevel2: ['（一）','（二）','（三）','（四）','（五）','（六）','（七）','（八）','（九）','（十）','（十一）','（十二）','（十三）','（十四）','（十五）','（十六）','（十七）','（十八）','（十九）','（二十）'],
    subjectCodeLevel3:1
}
window.investWithoutTotalNodeMsg = {
    subjectCodeLevel1:[ '一','二','三','四','五','六','七','八','九','十','十一','十二','十三','十四','十五','十六','十七','十八','十九'],
    subjectCodeLevel2: ['（一）','（二）','（三）','（四）','（五）','（六）','（七）','（八）','（九）','（十）','（十一）','（十二）','（十三）','（十四）','（十五）','（十六）','（十七）','（十八）','（十九）','（二十）'],
    subjectCodeLevel3:1
}
/**
 *
 * @param pcode
 * @returns {*}
 */
function createSubjectCode(tree, pnode) {
    if (!pnode) {
        return window.investMsg.subjectCodeLevel1[tree.getRootNode().children.length];
    }

    if (pnode._level == 0) {
        if (pnode.subjectCode != '一') {
            if (pnode.children && pnode.children.length > 0) {
                var pcode = pnode.children[pnode.children.length-1].subjectCode;
                var dotindex = pcode.lastIndexOf('.');
                var lastnum = pcode.substring(dotindex+1);
                var prenum = pcode.substring(0, dotindex+1);

                return ''+prenum + (parseInt(lastnum) + 1);
            }
            return '1';
        } else {
            if (pnode.children && pnode.children.length > 0) {
                return window.investMsg.subjectCodeLevel2[pnode.children.length];
            }
        }

        return '（一）';
    }
    else if (pnode._level == 1) {
        if (tree.getParentNode(pnode).subjectCode == '一') {
            if (pnode.children && pnode.children.length > 0) {
                var pcode = pnode.children[pnode.children.length-1].subjectCode;
                var dotindex = pcode.lastIndexOf('.');
                var lastnum = pcode.substring(dotindex+1);
                var prenum = pcode.substring(0, dotindex+1);

                return ''+prenum + (parseInt(lastnum) + 1);
            }
            return '1';
        } else {
            if (pnode.children && pnode.children.length > 0) {
                var pcode = pnode.children[pnode.children.length-1].subjectCode;
                var dotindex = pcode.lastIndexOf('.');
                var lastnum = pcode.substring(dotindex+1);
                var prenum = pcode.substring(0, dotindex+1);

                return ''+prenum + (parseInt(lastnum) + 1);
            }
            return pnode.subjectCode+'.1';
        }
    }
    if (pnode.children && pnode.children.length > 0) {
        var pcode = pnode.children[pnode.children.length-1].subjectCode;
        var dotindex = pcode.lastIndexOf('.');
        var lastnum = pcode.substring(dotindex+1);
        var prenum = pcode.substring(0, dotindex+1);

        return ''+prenum + (parseInt(lastnum) + 1);
    }
    return pnode.subjectCode+'.1';
}

function createSubjectCodeWithoutTotalNode(tree, pnode) {
    if (!pnode) {
        return window.investWithoutTotalNodeMsg.subjectCodeLevel1[tree.getRootNode().children.length];
    }
    if (pnode._level == 0) {
        if (pnode.subjectCode != '一') {
            if (pnode.children && pnode.children.length > 0) {
                var pcode = pnode.children[pnode.children.length].subjectCode;
                var dotindex = pcode.lastIndexOf('.');
                var lastnum = pcode.substring(dotindex+1);
                var prenum = pcode.substring(0, dotindex+1);

                return ''+prenum + (parseInt(lastnum) + 1);
            }
            return '1';
        } else {
            if (pnode.children && pnode.children.length > 0) {
                return window.investWithoutTotalNodeMsg.subjectCodeLevel2[pnode.children.length];
            }
        }

        return '（一）';
    }
    else if (pnode._level == 1) {
        if (tree.getParentNode(pnode).subjectCode == '一') {
            if (pnode.children && pnode.children.length > 0) {
                var pcode = pnode.children[pnode.children.length-1].subjectCode;
                var dotindex = pcode.lastIndexOf('.');
                var lastnum = pcode.substring(dotindex+1);
                var prenum = pcode.substring(0, dotindex+1);

                return ''+prenum + (parseInt(lastnum) + 1);
            }
            return '1';
        } else {
            if (pnode.children && pnode.children.length > 0) {
                var pcode = pnode.children[pnode.children.length-1].subjectCode;
                var dotindex = pcode.lastIndexOf('.');
                var lastnum = pcode.substring(dotindex+1);
                var prenum = pcode.substring(0, dotindex+1);

                return ''+prenum + (parseInt(lastnum) + 1);
            }
            return pnode.subjectCode+'.1';
        }
    }
    if (pnode.children && pnode.children.length > 0) {
        var pcode = pnode.children[pnode.children.length-1].subjectCode;
        var dotindex = pcode.lastIndexOf('.');
        var lastnum = pcode.substring(dotindex+1);
        var prenum = pcode.substring(0, dotindex+1);

        return ''+prenum + (parseInt(lastnum) + 1);
    }
    return pnode.subjectCode+'.1';
}

function createSubjectCode2(obj, node) {
    var subjectCode = null;
    if (node) {
        if (node.subjectCodeLevel1 != null) {
            subjectCode = window.investMsg.subjectCodeLevel2[obj.subjectCodeLevel2['level2'+node.subjectCodeLevel1]];
            obj.subjectCodeLevel2['level2'+node.subjectCodeLevel1]++;
        } else {
            //包括'）'，是第三级
            if (node.subjectCode.indexOf('）') == -1) {

            }
            //不包括的话，是第四级以上
            else {

            }
        }

        return subjectCode;
    }

    if (obj.subjectCodeLevel1 >= 20) {
        return null;
    }

    subjectCode = window.investMsg.subjectCodeLevel1[obj.subjectCodeLevel1];
    obj.subjectCodeLevel2['level2'+obj.subjectCodeLevel1] = 0;
    obj.subjectCodeLevel1++;

    return subjectCode;
}