USE `cpcep`;
ALTER TABLE bs_tech_bid_eval ADD scheme_comp_fileb VARCHAR(1000) ; /* 投标技术方案比较文件id */
ALTER TABLE bs_tech_bid_eval ADD business_prob_filec VARCHAR(1000) ; /* 与商务有关的问题文件id */
ALTER TABLE bs_tech_bid_eval ADD other_prob_filed VARCHAR(1000) ; /* 其他补充问题文件id */
ALTER TABLE bs_tech_bid_eval ADD conclusion_filee VARCHAR(1000) ; /* 结论文件id */