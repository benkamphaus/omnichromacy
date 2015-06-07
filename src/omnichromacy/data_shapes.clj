(ns omnichromacy.data-shapes
  (:use [clojure.core.matrix]))

(defn change-interleave
  "Rolled backuntil fixed -- C vs. Fortran ordering from data files."
  [] nil)

(defn as-spectral-matrix
  "Returns a :bip image cube as a spectral matrix (x,y dims flattened)"
  [img-cube]
  (let [dims (shape img-cube)]
    (reshape img-cube [(dims 0) (* (dims 1) (dims 2))])))
