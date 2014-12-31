(ns rs-clj.viz
  (:require [incanter.charts :refer [scatter-plot line-chart]]
            [clojure.core.matrix :refer [slice transpose]]))

(defn scatter
  "Scatter plot of a spectral matrix, must pass index for band1 and band2 as
  arguments"
  [spectral-mat b1-ind b2-ind]
  (let [b1-vals (into [] (slice spectral-mat b1-ind))
        b2-vals (into [] (slice spectral-mat b2-ind))]
    (scatter-plot b1-vals b2-vals)))

(defn spectral-plot
  "Plots spectrum at given pixel when passed spectral mat and collapsed coord"
  [spectral-mat coord]
  (let [spectrum (into [] (slice (transpose spectral-mat) coord))
        index (range (count spectrum))]
    (line-chart index spectrum)))
