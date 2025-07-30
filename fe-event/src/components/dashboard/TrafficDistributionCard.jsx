import Chart from "react-apexcharts";
import { ArrowUpLeft } from "lucide-react";

const TrafficDistributionCard = ({ revenueData }) => {
  // Tách dữ liệu từ props
  const ads = revenueData?.eventAdsRevenue ?? 0;
  const booking = revenueData?.ticketSellingRevenue ?? 0;

  const series = [ads, booking];
  const labels = ["Event Ads", "Ticket Booking"];

  const chartOptions = {
    series,
    labels,
    chart: {
      type: "donut",
      fontFamily: "'Plus Jakarta Sans', sans-serif",
      foreColor: "#c6d1e9",
    },
    tooltip: {
      theme: "dark",
      fillSeriesColor: false,
    },
    colors: ["#fb977d", "#0085db"], // Màu của "Ads" và "Booking"
    dataLabels: {
      enabled: false,
    },
    legend: {
      show: false,
    },
    stroke: {
      show: false,
    },
    responsive: [
      {
        breakpoint: 991,
        options: {
          chart: {
            width: 120,
          },
        },
      },
    ],
    plotOptions: {
      pie: {
        donut: {
          size: "80%",
          background: "none",
          labels: {
            show: true,
            name: {
              show: true,
              fontSize: "12px",
              offsetY: 5,
            },
            value: {
              show: false,
              color: "#98aab4",
            },
          },
        },
      },
    },
  };

  const total = ads + booking;

  return (
    <div className="card">
      <div className="card-body">
        <h4 className="text-gray-500 text-lg font-semibold mb-4">
          Traffic Distribution
        </h4>
        <div className="flex flex-col md:flex-row items-start md:items-center justify-between gap-4 md:gap-8">
          <div>
            <h3 className="text-[22px] font-semibold text-gray-500 mb-4">
              ${total.toLocaleString()}
            </h3>
            <div className="flex items-center gap-1 mb-3">
              <span className="flex items-center justify-center w-5 h-5 rounded-full bg-teal-400">
                <ArrowUpLeft className="text-teal-500" />
              </span>
              <p className="text-gray-500 text-sm font-normal">+9%</p>
              <p className="text-gray-400 text-sm font-normal text-nowrap">
                compared to last period
              </p>
            </div>
            <div className="flex gap-4">
              <div className="flex gap-2 items-center">
                <span className="w-2 h-2 rounded-full bg-red-400"></span>
                <p className="text-gray-400 font-normal text-xs">Event Ads</p>
              </div>
              <div className="flex gap-2 items-center">
                <span className="w-2 h-2 rounded-full bg-blue-600"></span>
                <p className="text-gray-400 font-normal text-xs">
                  Ticket Booking
                </p>
              </div>
            </div>
          </div>

          {/* Donut Chart */}
          <div className="chart-container w-full md:w-auto flex justify-center">
            <div style={{ width: "140px", height: "140px" }}>
              <Chart
                options={chartOptions}
                series={series}
                type="donut"
                width="100%"
                height="100%"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TrafficDistributionCard;
