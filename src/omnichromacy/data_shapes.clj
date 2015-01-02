(ns omnichromacy.data-shapes
  (:use [clojure.core.matrix]))

(defn change-interleave
  "Change an image cube interleave, e.g. from band sequential to by-pixel.
  fmt values are :bip :bsq :bil. Temporary broken implementation only goes
  from :bsq to :bil until core.matrix has ndarray transpose generalization
  implemented. Right now, just want pathways to get everything to bip."
  [img-cube from-fmt to-fmt]
  (let [dims (shape img-cube)]
    (cond (and (= from-fmt :bsq)
               (= to-fmt :bip)
               (= (count dims) 3))
            (-> img-cube (reshape [(* (dims 0) (dims 1)) (dims 2)])
                         (transpose)
                         (reshape [(dims 2) (dims 0) (dims 1)]))
          (and (= from-fmt :bil)
               (= to-fmt :bip)
               (= (count dims) 3))
            ; this is definitely the best way to do this.
            (change-interleave (->> img-cube (slices) (map transpose) (array)) :bsq :bip))))

(defn as-spectral-matrix
  "Returns a :bip image cube as a spectral matrix (x,y dims flattened)"
  [img-cube]
  (let [dims (shape img-cube)]
    (reshape img-cube [(dims 0) (* (dims 1) (dims 2))])))
