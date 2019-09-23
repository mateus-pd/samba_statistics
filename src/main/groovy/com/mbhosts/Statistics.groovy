package com.mbhosts

class Statistics {

    private Double sum
    private Double avg
    private Double max
    private Double min
    private Long count

    Statistics() {  }
    Statistics(Double _sum, Double _avg, Double _max, Double _min, Long _count) {
        this.sum = _sum
        this.avg = _avg
        this.max = _max
        this.min = _min
        this.count = _count
    }

    Double getSum() { return this.sum }
    Double getAvg() { return this.avg }
    Double getMax() { return this.max }
    Double getMin() { return this.min }
    Long getCount() { return this.count }

    void setSum(Double _sum) { this.sum = _sum }
    void setAvg(Double _avg) { this.avg = _avg }
    void setMax(Double _max) { this.max = _max }
    void setMin(Double _min) { this.min = _min }
    void setCount(Long _count) { this.count = _count }

}