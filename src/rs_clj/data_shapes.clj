(ns rs-clj.data-shapes
  (:use [clojure.core.matrix]))

(defn change-interleave
  "Change an image cube interleave, e.g. from band sequential to by-pixel.
  fmt values are :bip :bsq :bil. Temporary broken implementation only goes
  from :bsq to :bil until core.matrix has ndarray transpose generalization
  implemented."
  [img-cube from-fmt to-fmt]
  (let [dims (shape img-cube)]
    (if (and (= from-fmt :bsq)
             (= to-fmt :bip)
             (= (count dims) 3))
      (-> img-cube (reshape [(* (dims 0) (dims 1)) (dims 2)])
                   (transpose)
                   (reshape [(dims 2) (dims 0) (dims 1)])))))

(defn as-spectral-matrix
  "Returns a :bip image cube as a spectral matrix (x,y dims flattened)"
  [img-cube]
  (let [dims (shape img-cube)]
    (reshape img-cube [(dims 0) (* (dims 1) (dims 2))])))
